import { NextRequest, NextResponse } from "next/server";
import { prisma } from "@/lib/prisma";

export async function GET(req: NextRequest) {
  const { searchParams } = new URL(req.url);
  const concern = searchParams.get("concern");
  const q = searchParams.get("q");

  const products = await prisma.product.findMany({
    where: {
      AND: [
        concern
          ? { suitableFor: { some: { name: { equals: concern } } } }
          : {},
        q
          ? {
              OR: [
                { name: { contains: q } },
                { brand: { contains: q } }
              ]
            }
          : {}
      ]
    },
    orderBy: [{ ratingAverage: "desc" }, { ratingCount: "desc" }]
  });

  return NextResponse.json(products);
}
