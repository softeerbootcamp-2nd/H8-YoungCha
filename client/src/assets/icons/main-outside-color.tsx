interface MainOutsideColorProps {
  code: string;
}

function MainOutsideColor({ code }: MainOutsideColorProps) {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      width="22"
      height="22"
      viewBox="0 0 22 22"
      fill="none"
    >
      <circle
        cx="11"
        cy="11"
        r="10.5"
        fill={code}
        stroke={code === '#FAFAFA' ? '#D1D9E3' : ''}
      />
    </svg>
  );
}

export default MainOutsideColor;
