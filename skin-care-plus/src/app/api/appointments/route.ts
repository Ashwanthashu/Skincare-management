import { NextRequest, NextResponse } from 'next/server'

export async function POST(req: NextRequest) {
	const body = await req.json().catch(() => ({})) as { location?: string }
	const location = body.location ?? 'your area'
	// Placeholder: static providers
	const providers = [
		{ id: 'demo-1', name: 'Derm Clinic One', distanceKm: 2.1, rating: 4.6 },
		{ id: 'demo-2', name: 'ClearSkin Center', distanceKm: 4.3, rating: 4.3 },
	]
	return NextResponse.json({ location, providers })
}
