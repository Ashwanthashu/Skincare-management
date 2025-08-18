import { NextRequest, NextResponse } from 'next/server'

export async function POST(req: NextRequest) {
	const body = await req.json().catch(() => ({})) as { query?: string, concerns?: string[] }
	const query = body.query ?? 'skin care'
	const concerns = body.concerns ?? []
	// Placeholder: static products
	const products = [
		{ id: 'prod-1', name: 'Gentle Cleanser', price: 12.99, store: 'DemoMart' },
		{ id: 'prod-2', name: 'Hydrating Serum', price: 24.5, store: 'ShopNow' },
	]
	return NextResponse.json({ query, concerns, products })
}
