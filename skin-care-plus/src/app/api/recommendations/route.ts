import { NextRequest, NextResponse } from 'next/server'

export async function POST(req: NextRequest) {
	const body = await req.json().catch(() => ({})) as { concerns?: string[] }
	const concerns = body.concerns ?? []
	// Placeholder: return basic static tips based on concerns
	const tips = concerns.length > 0 ? concerns.map(c => `General advice for ${c}`) : ['Hydrate well', 'Use sunscreen (SPF 30+)', 'Get enough sleep']
	return NextResponse.json({ tips })
}
