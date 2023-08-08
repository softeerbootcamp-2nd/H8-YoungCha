interface SelectedBarProps {
  active: number;
}

function SelectedBar({ active }: SelectedBarProps) {
  return (
    <div
      className="absolute flex text-center justify-center bg-main-blue w-[120px] h-[2px] top-[21px] z-10 ease-in-out duration-300"
      style={{ left: `${active * 120}px` }}
    ></div>
  );
}

export default SelectedBar;
