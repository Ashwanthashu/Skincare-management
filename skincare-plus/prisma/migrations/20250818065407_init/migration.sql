-- CreateTable
CREATE TABLE "User" (
    "id" TEXT NOT NULL PRIMARY KEY,
    "email" TEXT NOT NULL,
    "name" TEXT,
    "skinType" TEXT,
    "createdAt" DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updatedAt" DATETIME NOT NULL
);

-- CreateTable
CREATE TABLE "SkinAnalysis" (
    "id" TEXT NOT NULL PRIMARY KEY,
    "userId" TEXT NOT NULL,
    "imageUrl" TEXT,
    "notes" TEXT,
    "scoreHydration" INTEGER,
    "scoreAcne" INTEGER,
    "scoreTexture" INTEGER,
    "createdAt" DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT "SkinAnalysis_userId_fkey" FOREIGN KEY ("userId") REFERENCES "User" ("id") ON DELETE RESTRICT ON UPDATE CASCADE
);

-- CreateTable
CREATE TABLE "Concern" (
    "id" TEXT NOT NULL PRIMARY KEY,
    "name" TEXT NOT NULL,
    "description" TEXT
);

-- CreateTable
CREATE TABLE "Product" (
    "id" TEXT NOT NULL PRIMARY KEY,
    "name" TEXT NOT NULL,
    "brand" TEXT NOT NULL,
    "priceCents" INTEGER NOT NULL,
    "currency" TEXT NOT NULL DEFAULT 'USD',
    "productUrl" TEXT NOT NULL,
    "imageUrl" TEXT,
    "retailer" TEXT NOT NULL,
    "ingredients" TEXT,
    "ratingAverage" REAL DEFAULT 0,
    "ratingCount" INTEGER DEFAULT 0,
    "createdAt" DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- CreateTable
CREATE TABLE "Appointment" (
    "id" TEXT NOT NULL PRIMARY KEY,
    "userId" TEXT NOT NULL,
    "dermatologist" TEXT NOT NULL,
    "location" TEXT NOT NULL,
    "startTime" DATETIME NOT NULL,
    "endTime" DATETIME NOT NULL,
    "notes" TEXT,
    "createdAt" DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT "Appointment_userId_fkey" FOREIGN KEY ("userId") REFERENCES "User" ("id") ON DELETE RESTRICT ON UPDATE CASCADE
);

-- CreateTable
CREATE TABLE "_ConcernToUser" (
    "A" TEXT NOT NULL,
    "B" TEXT NOT NULL,
    CONSTRAINT "_ConcernToUser_A_fkey" FOREIGN KEY ("A") REFERENCES "Concern" ("id") ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT "_ConcernToUser_B_fkey" FOREIGN KEY ("B") REFERENCES "User" ("id") ON DELETE CASCADE ON UPDATE CASCADE
);

-- CreateTable
CREATE TABLE "_ConcernToSkinAnalysis" (
    "A" TEXT NOT NULL,
    "B" TEXT NOT NULL,
    CONSTRAINT "_ConcernToSkinAnalysis_A_fkey" FOREIGN KEY ("A") REFERENCES "Concern" ("id") ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT "_ConcernToSkinAnalysis_B_fkey" FOREIGN KEY ("B") REFERENCES "SkinAnalysis" ("id") ON DELETE CASCADE ON UPDATE CASCADE
);

-- CreateTable
CREATE TABLE "_ConcernToProduct" (
    "A" TEXT NOT NULL,
    "B" TEXT NOT NULL,
    CONSTRAINT "_ConcernToProduct_A_fkey" FOREIGN KEY ("A") REFERENCES "Concern" ("id") ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT "_ConcernToProduct_B_fkey" FOREIGN KEY ("B") REFERENCES "Product" ("id") ON DELETE CASCADE ON UPDATE CASCADE
);

-- CreateIndex
CREATE UNIQUE INDEX "User_email_key" ON "User"("email");

-- CreateIndex
CREATE UNIQUE INDEX "Concern_name_key" ON "Concern"("name");

-- CreateIndex
CREATE UNIQUE INDEX "Product_name_brand_key" ON "Product"("name", "brand");

-- CreateIndex
CREATE UNIQUE INDEX "_ConcernToUser_AB_unique" ON "_ConcernToUser"("A", "B");

-- CreateIndex
CREATE INDEX "_ConcernToUser_B_index" ON "_ConcernToUser"("B");

-- CreateIndex
CREATE UNIQUE INDEX "_ConcernToSkinAnalysis_AB_unique" ON "_ConcernToSkinAnalysis"("A", "B");

-- CreateIndex
CREATE INDEX "_ConcernToSkinAnalysis_B_index" ON "_ConcernToSkinAnalysis"("B");

-- CreateIndex
CREATE UNIQUE INDEX "_ConcernToProduct_AB_unique" ON "_ConcernToProduct"("A", "B");

-- CreateIndex
CREATE INDEX "_ConcernToProduct_B_index" ON "_ConcernToProduct"("B");
