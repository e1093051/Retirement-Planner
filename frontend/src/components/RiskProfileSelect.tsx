import { useEffect, useMemo, useState } from "react";
import type { RiskProfileKey, RiskProfilesResponse, AllocationsResponse  } from "../api/types";
import { getRiskProfiles, getAllocations } from "../api/client";



export default function RiskProfileSelect({
  value,
  onChange,
  disabled,
}: {
  value: RiskProfileKey;
  onChange: (v: RiskProfileKey) => void;
  disabled: boolean;
}) {
  const [profiles, setProfiles] = useState<RiskProfilesResponse | null>(null);
  const [allocations, setAllocations] = useState<AllocationsResponse | null>(null);

  useEffect(() => {
    getRiskProfiles()
      .then(setProfiles)
      .catch(() => setProfiles(null));

    getAllocations()
      .then(setAllocations)
      .catch(() => setAllocations(null));
  }, []);

  const tooltipHtml = useMemo(() => {
    if (!allocations) return "Portfolio assumptions unavailable.";

    const order: RiskProfileKey[] = ["conservative", "balanced", "aggressive"];

    return order
      .filter((k) => allocations[k])
      .map((k) => {
        const { symbols, weights, period } = allocations[k];

        const name = k[0].toUpperCase() + k.slice(1);

        const weightPct = weights
          .split(",")
          .map((w) => `${Math.round(Number(w) * 100)}%`)
          .join(" / ");

        return `${name}: ${symbols} (${weightPct}), based on ${period} historical data`;
      })
      .join("\n");
  }, [allocations]);


  return (
    <>
      <label className="label-row">
        Risk Profile
        <span className="info" tabIndex={0} aria-label="Risk profile assumptions">
          ⓘ
          <span className="tooltip">
            {tooltipHtml.split("\n").map((line) => (
              <span key={line}>
                {line}
                <br />
              </span>
            ))}
          </span>
        </span>
      </label>

      <select value={value} onChange={(e) => onChange(e.target.value as RiskProfileKey)} disabled={disabled}>
        <option value="conservative">conservative</option>
        <option value="balanced">balanced</option>
        <option value="aggressive">aggressive</option>
      </select>
    </>
  );
}
