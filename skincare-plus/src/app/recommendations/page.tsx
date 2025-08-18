"use client";
import { useEffect, useState } from "react";

type Concern = { id: string; name: string };
type Product = { id: string; name: string; brand: string; priceCents: number; imageUrl?: string | null; ratingAverage?: number | null };

export default function RecommendationsPage() {
  const [concerns, setConcerns] = useState<Concern[]>([]);
  const [selected, setSelected] = useState<string[]>([]);
  const [products, setProducts] = useState<Product[]>([]);

  useEffect(() => {
    fetch("/api/concerns").then(r => r.json()).then(setConcerns);
  }, []);

  useEffect(() => {
    const c = selected[0];
    if (!c) { setProducts([]); return; }
    fetch(`/api/products?concern=${encodeURIComponent(c)}`).then(r => r.json()).then(setProducts);
  }, [selected]);

  return (
    <main className="max-w-5xl mx-auto px-6 py-10">
      <h1 className="text-2xl font-semibold">Recommendations</h1>
      <div className="mt-4 flex gap-2 flex-wrap">
        {concerns.map(c => (
          <button key={c.id} onClick={() => setSelected([c.name])} className={`px-3 py-1 rounded border ${selected.includes(c.name) ? 'bg-black text-white' : ''}`}>
            {c.name}
          </button>
        ))}
      </div>
      <div className="mt-6 grid sm:grid-cols-2 md:grid-cols-3 gap-4">
        {products.map(p => (
          <div key={p.id} className="border rounded p-4">
            <div className="font-medium">{p.brand} — {p.name}</div>
            <div className="text-sm text-gray-600">${(p.priceCents/100).toFixed(2)} • {p.ratingAverage ?? 0}★</div>
          </div>
        ))}
      </div>
    </main>
  );
}

