# SkinCare+ — AI-Powered Skin Care Monitoring & Recommendation System

## Getting Started

1. Install dependencies:
   - npm install
2. Generate Prisma client:
   - npm run prisma:generate
3. Run the dev server on port 3000:
   - npm run dev

## Environment

- DATABASE_URL: SQLite DB path (default: file:./dev.db)

## API Routes

- GET /api/health — Health check
- POST /api/recommendations { concerns: string[] }
- POST /api/appointments { location: string }
- POST /api/products { query: string, concerns: string[] }

## Next Steps

- Implement auth and user profiles
- Add image upload and analysis (AI)
- Real provider and product integrations
- Personalized recommendation engine
