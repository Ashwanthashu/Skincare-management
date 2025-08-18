import Link from "next/link";

export default function Landing() {
  return (
    <main className="min-h-screen px-6 py-10 mx-auto max-w-5xl">
      <h1 className="text-3xl font-semibold">SkinCare+ — AI-Powered Skin Care</h1>
      <p className="mt-2 text-gray-600">Monitor skin health, get personalized advice, book dermatologists, and find products.</p>

      <div className="grid md:grid-cols-2 gap-6 mt-8">
        <Section title="Analyze Skin" href="/analyze" desc="Upload a photo to get heuristic analysis scores." />
        <Section title="Recommendations" href="/recommendations" desc="Get product picks based on your concerns." />
        <Section title="Appointments" href="/appointments" desc="Find and book dermatologist appointments." />
        <Section title="Products" href="/products" desc="Browse products filtered by concerns." />
      </div>
    </main>
  );
}

function Section({ title, href, desc }: { title: string; href: string; desc: string }) {
  return (
    <Link href={href} className="border rounded-lg p-6 hover:shadow-md transition">
      <h2 className="text-xl font-medium">{title}</h2>
      <p className="text-gray-600 mt-1">{desc}</p>
    </Link>
  );
}

