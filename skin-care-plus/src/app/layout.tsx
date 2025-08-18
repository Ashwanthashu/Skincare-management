import type { Metadata } from 'next'
import '../styles/globals.css'

export const metadata: Metadata = {
	title: 'SkinCare+ — AI Skin Monitoring',
	description: 'Monitor your skin health, get personalized advice, book dermatologists, and find products.',
}

export default function RootLayout({ children }: { children: React.ReactNode }) {
	return (
		<html lang="en">
			<body className="min-h-screen antialiased bg-white text-gray-900">
				<header className="border-b">
					<div className="mx-auto max-w-6xl px-4 py-4 flex items-center justify-between">
						<a href="/" className="font-semibold">SkinCare+</a>
						<nav className="flex gap-4 text-sm">
							<a href="/monitor">Monitor</a>
							<a href="/recommendations">Recommendations</a>
							<a href="/appointments">Appointments</a>
							<a href="/products">Products</a>
						</nav>
					</div>
				</header>
				<main className="mx-auto max-w-6xl px-4 py-8">{children}</main>
			</body>
		</html>
	)
}
