interface ModifyButtonProps {
  onClick: () => void;
}

function ModifyButton({ onClick }: ModifyButtonProps) {
  return (
    <button className="flex gap-6px" onClick={onClick}>
      <svg
        xmlns="http://www.w3.org/2000/svg"
        width="20"
        height="20"
        viewBox="0 0 20 20"
        fill="none"
      >
        <path
          d="M5.345 13.3332L13.7967 4.88157L12.6183 3.70324L4.16667 12.1549V13.3332H5.345ZM6.03583 14.9999H2.5V11.4641L12.0292 1.93491C12.1854 1.77868 12.3974 1.69092 12.6183 1.69092C12.8393 1.69092 13.0512 1.77868 13.2075 1.93491L15.565 4.29241C15.7212 4.44868 15.809 4.6606 15.809 4.88157C15.809 5.10254 15.7212 5.31447 15.565 5.47074L6.03583 14.9999V14.9999ZM2.5 16.6666H17.5V18.3332H2.5V16.6666Z"
          fill="#4CA7CE"
        />
      </svg>
      <span className="text-sub-blue font-hsans-text text-18px leading-[23.4px] tracking-[-0.54px]">
        수정
      </span>
    </button>
  );
}

export default ModifyButton;
