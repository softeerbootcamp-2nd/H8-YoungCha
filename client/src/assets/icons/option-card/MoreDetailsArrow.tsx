import { SVGAttributes } from 'react';

function MoreDetailsArrow({ ...props }: SVGAttributes<SVGSVGElement>) {
  return (
    <svg
      width="14"
      height="14"
      viewBox="0 0 14 14"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
      {...props}
    >
      <g id="Group">
        <path
          id="Vector"
          d="M7.77794 7.84802L12.0127 3.88813L13.2224 5.0193L7.77794 10.1104L2.3335 5.0193L3.54318 3.88813L7.77794 7.84802Z"
          fill="#A5ACB8"
        />
      </g>
    </svg>
  );
}

export default MoreDetailsArrow;
