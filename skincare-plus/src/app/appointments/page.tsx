"use client";
import { useEffect, useState } from "react";

type Appointment = {
  id: string;
  dermatologist: string;
  location: string;
  startTime: string;
  endTime: string;
  notes?: string | null;
};

export default function AppointmentsPage() {
  const [items, setItems] = useState<Appointment[]>([]);
  const [form, setForm] = useState({ dermatologist: "", location: "", startTime: "", endTime: "" });

  const load = async () => {
    const data = await fetch("/api/appointments?userId=demo-user-id").then(r => r.json());
    setItems(data);
  };

  useEffect(() => { load(); }, []);

  const submit = async () => {
    await fetch("/api/appointments", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ userId: "demo-user-id", ...form })
    });
    setForm({ dermatologist: "", location: "", startTime: "", endTime: "" });
    await load();
  };

  return (
    <main className="max-w-4xl mx-auto px-6 py-10">
      <h1 className="text-2xl font-semibold">Appointments</h1>
      <div className="mt-4 grid sm:grid-cols-2 gap-4">
        <input placeholder="Dermatologist" value={form.dermatologist} onChange={e=>setForm(f=>({...f, dermatologist:e.target.value}))} className="border rounded p-2" />
        <input placeholder="Location" value={form.location} onChange={e=>setForm(f=>({...f, location:e.target.value}))} className="border rounded p-2" />
        <input type="datetime-local" value={form.startTime} onChange={e=>setForm(f=>({...f, startTime:e.target.value}))} className="border rounded p-2" />
        <input type="datetime-local" value={form.endTime} onChange={e=>setForm(f=>({...f, endTime:e.target.value}))} className="border rounded p-2" />
      </div>
      <button onClick={submit} className="mt-3 px-4 py-2 bg-black text-white rounded">Book</button>

      <div className="mt-6 space-y-3">
        {items.map(a => (
          <div key={a.id} className="border rounded p-4">
            <div className="font-medium">{a.dermatologist}</div>
            <div className="text-sm text-gray-600">{a.location}</div>
            <div className="text-sm">{new Date(a.startTime).toLocaleString()} → {new Date(a.endTime).toLocaleString()}</div>
          </div>
        ))}
      </div>
    </main>
  );
}

