import { NextRequest, NextResponse } from "next/server";
import { prisma } from "@/lib/prisma";
import { z } from "zod";

const createSchema = z.object({
  userId: z.string().cuid(),
  dermatologist: z.string().min(1),
  location: z.string().min(1),
  startTime: z.string().datetime(),
  endTime: z.string().datetime(),
  notes: z.string().optional(),
});

export async function GET(req: NextRequest) {
  const { searchParams } = new URL(req.url);
  const userId = searchParams.get("userId");
  let where: { userId?: string } = {};
  if (userId) {
    if (userId === "demo-user-id") {
      const user = await prisma.user.findFirst({ where: { email: "demo@skincare.plus" } });
      where = user ? { userId: user.id } : { userId: "__none__" };
    } else {
      where = { userId };
    }
  }
  const appts = await prisma.appointment.findMany({
    where,
    orderBy: { startTime: "asc" }
  });
  return NextResponse.json(appts);
}

export async function POST(req: NextRequest) {
  const body = await req.json();
  const parsed = createSchema.safeParse(body);
  if (!parsed.success) {
    return NextResponse.json({ error: parsed.error.format() }, { status: 400 });
  }

  // Resolve user id
  const user = parsed.data.userId === "demo-user-id"
    ? await prisma.user.findFirst({ where: { email: "demo@skincare.plus" } })
    : await prisma.user.findUnique({ where: { id: parsed.data.userId } });
  if (!user) return NextResponse.json({ error: "User not found" }, { status: 404 });

  const created = await prisma.appointment.create({
    data: {
      userId: user.id,
      dermatologist: parsed.data.dermatologist,
      location: parsed.data.location,
      startTime: new Date(parsed.data.startTime),
      endTime: new Date(parsed.data.endTime),
      notes: parsed.data.notes,
    }
  });
  return NextResponse.json(created, { status: 201 });
}
