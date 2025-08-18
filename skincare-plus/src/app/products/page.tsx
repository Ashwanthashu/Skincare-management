"use client";
import { useEffect, useState } from "react";

type Product = { id: string; name: string; brand: string; priceCents: number; productUrl: string; ratingAverage?: number | null };

export default function ProductsPage() {
  const [q, setQ] = useState("");
  const [items, setItems] = useState<Product[]>([]);

  const search = async () => {
    const data = await fetch(`/api/products?q=${encodeURIComponent(q)}`).then(r => r.json());
    setItems(data);
  };

  useEffect(() => { search(); }, []); // eslint-disable-line react-hooks/exhaustive-deps

  return (
    <main className="max-w-5xl mx-auto px-6 py-10">
      <h1 className="text-2xl font-semibold">Products</h1>
      <div className="mt-4 flex gap-2">
        <input value={q} onChange={e=>setQ(e.target.value)} placeholder="Search products or brands" className="border rounded p-2 flex-1" />
        <button onClick={search} className="px-4 py-2 bg-black text-white rounded">Search</button>
      </div>
      <div className="mt-6 grid sm:grid-cols-2 md:grid-cols-3 gap-4">
        {items.map(p => (
          <a key={p.id} href={p.productUrl} target="_blank" className="border rounded p-4 hover:shadow">
            <div className="font-medium">{p.brand} — {p.name}</div>
            <div className="text-sm text-gray-600">${(p.priceCents/100).toFixed(2)} • {p.ratingAverage ?? 0}★</div>
          </a>
        ))}
      </div>
    </main>
  );
}

