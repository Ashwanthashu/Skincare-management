export default function HomePage() {
	return (
		<section className="space-y-6">
			<h1 className="text-2xl font-semibold">SkinCare+ — AI-Powered Skin Care</h1>
			<p className="text-gray-600 max-w-2xl">
				Monitor your skin health, get personalized skincare advice, book dermatologist appointments nearby,
				and discover cosmetic products tailored to your skin concerns.
			</p>
			<div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
				<a className="rounded-lg border p-4 hover:bg-gray-50" href="/monitor">
					<h2 className="font-medium">Monitor</h2>
					<p className="text-sm text-gray-600">Track logs and upload photos over time.</p>
				</a>
				<a className="rounded-lg border p-4 hover:bg-gray-50" href="/recommendations">
					<h2 className="font-medium">Recommendations</h2>
					<p className="text-sm text-gray-600">Get AI tips based on your skin profile.</p>
				</a>
				<a className="rounded-lg border p-4 hover:bg-gray-50" href="/appointments">
					<h2 className="font-medium">Appointments</h2>
					<p className="text-sm text-gray-600">Find dermatologists near you.</p>
				</a>
				<a className="rounded-lg border p-4 hover:bg-gray-50" href="/products">
					<h2 className="font-medium">Products</h2>
					<p className="text-sm text-gray-600">Search products matching your concerns.</p>
				</a>
			</div>
		</section>
	)
}
