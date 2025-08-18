import { PrismaClient, SkinType } from "../src/generated/prisma";

const prisma = new PrismaClient();

async function main() {
  // Concerns via upsert
  const concernData = [
    { name: "Acne", description: "Breakouts, pimples, blackheads" },
    { name: "Dryness", description: "Tight feeling, flakiness" },
    { name: "Oiliness", description: "Shine, enlarged pores" },
    { name: "Sensitivity", description: "Redness, irritation" },
    { name: "Hyperpigmentation", description: "Dark spots" },
    { name: "Texture", description: "Rough or uneven texture" },
    { name: "Fine Lines", description: "Fine lines and wrinkles" }
  ];
  for (const c of concernData) {
    await prisma.concern.upsert({
      where: { name: c.name },
      update: {},
      create: c,
    });
  }

  // Products (sample)
  const acne = await prisma.concern.findFirst({ where: { name: "Acne" } });
  const dryness = await prisma.concern.findFirst({ where: { name: "Dryness" } });
  const oiliness = await prisma.concern.findFirst({ where: { name: "Oiliness" } });

  const productUpserts = [
    {
      name: "CeraVe Foaming Cleanser",
      brand: "CeraVe",
      priceCents: 1299,
      currency: "USD",
      productUrl: "https://www.cerave.com/",
      imageUrl: "https://images.example.com/cerave-foaming.jpg",
      retailer: "CeraVe",
      suitableFor: [oiliness?.id, acne?.id].filter(Boolean) as string[],
      ingredients: "Niacinamide, Ceramides",
      ratingAverage: 4.5,
      ratingCount: 1023
    },
    {
      name: "La Roche-Posay Toleriane Hydrating Cleanser",
      brand: "La Roche-Posay",
      priceCents: 1499,
      currency: "USD",
      productUrl: "https://www.laroche-posay.us/",
      imageUrl: "https://images.example.com/lrp-toleriane.jpg",
      retailer: "La Roche-Posay",
      suitableFor: [dryness?.id].filter(Boolean) as string[],
      ingredients: "Ceramides, Niacinamide",
      ratingAverage: 4.6,
      ratingCount: 865
    }
  ];

  for (const p of productUpserts) {
    await prisma.product.upsert({
      where: { name_brand: { name: p.name, brand: p.brand } },
      update: {},
      create: {
        name: p.name,
        brand: p.brand,
        priceCents: p.priceCents,
        currency: p.currency,
        productUrl: p.productUrl,
        imageUrl: p.imageUrl,
        retailer: p.retailer,
        ingredients: p.ingredients,
        ratingAverage: p.ratingAverage,
        ratingCount: p.ratingCount,
        suitableFor: {
          connect: p.suitableFor.map((id) => ({ id }))
        }
      }
    });
  }

  // Demo user
  await prisma.user.upsert({
    where: { email: "demo@skincare.plus" },
    update: {},
    create: {
      email: "demo@skincare.plus",
      name: "Demo User",
      skinType: SkinType.COMBINATION,
      concerns: {
        connect: ["Acne", "Oiliness"].map((name) => ({ name }))
      }
    }
  });

  console.log("Seed completed");
}

main()
  .catch((e) => {
    console.error(e);
    process.exit(1);
  })
  .finally(async () => {
    await prisma.$disconnect();
  });

