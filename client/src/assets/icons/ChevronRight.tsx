function ChevronRight({ size = 20 }: { size?: number }) {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      width={size}
      height={size}
      fill="none"
      viewBox="0 0 20 20"
    >
      <path
        fill="currentColor"
        d="M10.977 10 6.852 5.877 8.03 4.697l5.303 5.304-5.303 5.303-1.178-1.178L10.977 10Z"
      />
    </svg>
  );
}

export default ChevronRight;
