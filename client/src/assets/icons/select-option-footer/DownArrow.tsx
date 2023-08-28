import { SVGProps } from 'react';

function DownArrow(props: SVGProps<SVGSVGElement>) {
  return (
    <svg
      width="17"
      height="16"
      viewBox="0 0 17 16"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
      {...props}
    >
      <path d="M8.60841 7.21867L5.30841 10.5187L4.36574 9.576L8.60841 5.33334L12.8511 9.576L11.9084 10.5187L8.60841 7.21867Z" />
    </svg>
  );
}

export default DownArrow;
