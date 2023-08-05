function MainOutsideWhite() {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      width="42"
      height="42"
      viewBox="0 0 42 42"
      fill="none"
    >
      <g filter="url(#filter0_d_609_19565)">
        <circle cx="21" cy="21" r="10" fill="#FAFAFA" />
        <circle cx="21" cy="21" r="10.5" stroke="#DBDEE3" />
      </g>
      <defs>
        <filter
          id="filter0_d_609_19565"
          x="0"
          y="0"
          width="42"
          height="42"
          filterUnits="userSpaceOnUse"
          colorInterpolationFilters="sRGB"
        >
          <feFlood floodOpacity="0" result="BackgroundImageFix" />
          <feColorMatrix
            in="SourceAlpha"
            type="matrix"
            values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0"
            result="hardAlpha"
          />
          <feOffset />
          <feGaussianBlur stdDeviation="5" />
          <feComposite in2="hardAlpha" operator="out" />
          <feColorMatrix
            type="matrix"
            values="0 0 0 0 0.929412 0 0 0 0 0.929412 0 0 0 0 0.929412 0 0 0 0.25 0"
          />
          <feBlend
            mode="normal"
            in2="BackgroundImageFix"
            result="effect1_dropShadow_609_19565"
          />
          <feBlend
            mode="normal"
            in="SourceGraphic"
            in2="effect1_dropShadow_609_19565"
            result="shape"
          />
        </filter>
      </defs>
    </svg>
  );
}
export default MainOutsideWhite;
