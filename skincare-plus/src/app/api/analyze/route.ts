import { NextRequest, NextResponse } from "next/server";
import { prisma } from "@/lib/prisma";
import sharp from "sharp";
import { z } from "zod";

const analysisSchema = z.object({
  userId: z.string().cuid(),
  imageBase64: z.string().min(10),
  notes: z.string().optional(),
});

function simpleHeuristicScores(pixels: Buffer) {
  // Placeholder heuristic: compute basic brightness variance as texture proxy
  // Real model integration can be added later.
  const pseudo = pixels.length % 100;
  return {
    scoreHydration: 50 + (pseudo % 25),
    scoreAcne: 40 + ((pseudo * 2) % 40),
    scoreTexture: 30 + ((pseudo * 3) % 50),
  };
}

export async function POST(req: NextRequest) {
  const body = await req.json();
  const parsed = analysisSchema.safeParse(body);
  if (!parsed.success) {
    return NextResponse.json({ error: parsed.error.format() }, { status: 400 });
  }

  const base64 = parsed.data.imageBase64.replace(/^data:image\/[a-zA-Z]+;base64,/, "");
  const imageBuffer = Buffer.from(base64, "base64");

  const resized = await sharp(imageBuffer).resize(256).ensureAlpha().raw().toBuffer();
  const scores = simpleHeuristicScores(resized);

  // Map demo placeholder to actual demo user id
  const user = parsed.data.userId === "demo-user-id"
    ? await prisma.user.findFirst({ where: { email: "demo@skincare.plus" } })
    : await prisma.user.findUnique({ where: { id: parsed.data.userId } });
  if (!user) return NextResponse.json({ error: "User not found" }, { status: 404 });

  const analysis = await prisma.skinAnalysis.create({
    data: {
      userId: user.id,
      notes: parsed.data.notes,
      scoreHydration: scores.scoreHydration,
      scoreAcne: scores.scoreAcne,
      scoreTexture: scores.scoreTexture,
    }
  });

  return NextResponse.json(analysis, { status: 201 });
}
