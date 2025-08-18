"use client";
import { useState } from "react";

type AnalysisResult = {
  id: string;
  userId: string;
  scoreHydration?: number | null;
  scoreAcne?: number | null;
  scoreTexture?: number | null;
  createdAt: string;
};

export default function AnalyzePage() {
  const [image, setImage] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);
  const [result, setResult] = useState<AnalysisResult | null>(null);

  const handleFile = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (!file) return;
    const reader = new FileReader();
    reader.onload = () => setImage(reader.result as string);
    reader.readAsDataURL(file);
  };

  const submit = async () => {
    if (!image) return;
    setLoading(true);
    const res = await fetch("/api/analyze", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ userId: "demo-user-id", imageBase64: image })
    });
    const data = await res.json();
    setResult(data);
    setLoading(false);
  };

  return (
    <main className="max-w-3xl mx-auto px-6 py-10">
      <h1 className="text-2xl font-semibold">Analyze Skin</h1>
      <div className="mt-4 space-y-4">
        <input type="file" accept="image/*" onChange={handleFile} />
        <button onClick={submit} disabled={!image || loading} className="px-4 py-2 rounded bg-black text-white disabled:opacity-50">
          {loading ? "Analyzing..." : "Analyze"}
        </button>
      </div>
      {result && (
        <div className="mt-6 border rounded p-4">
          <h2 className="font-medium">Results</h2>
          <pre className="mt-2 text-sm whitespace-pre-wrap">{JSON.stringify(result, null, 2)}</pre>
        </div>
      )}
    </main>
  );
}

