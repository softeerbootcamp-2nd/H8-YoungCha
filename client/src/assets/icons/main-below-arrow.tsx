interface MainBelowArrowProps {
  opacity: number;
  className?: string;
}

function MainBelowArrow({ opacity, className }: MainBelowArrowProps) {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      width={26}
      height={26}
      viewBox="0 0 26 26"
      fill="none"
      className={className}
    >
      <g opacity={opacity}>
        <path
          d="M13.0002 14.2687L18.3627 8.90625L19.8945 10.4381L13.0002 17.3324L6.10586 10.4381L7.6377 8.90625L13.0002 14.2687Z"
          fill="white"
        />
      </g>
    </svg>
  );
}

export default MainBelowArrow;
