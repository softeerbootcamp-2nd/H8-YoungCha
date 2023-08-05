function CarsImageBox() {
  return (
    <ul className="flex justify-between">
      <li className="w-[214px] h-[155px] flex flex-col items-center gap-[8px]">
        <img src="src/assets/images/palisade.png" alt="palisade" />
        <p className="font-normal body1 text-grey-black">38,960,000원 부터</p>
      </li>
      <li className="w-[214px] h-[155px] flex flex-col items-center gap-[8px]">
        <img src="src/assets/images/palisade.png" alt="palisade" />
        <p className="font-normal body1 text-grey-black">38,960,000원 부터</p>
      </li>
      <li className=" flex flex-col items-center gap-[8px]">
        <img
          src="src/assets/images/palisade.png"
          alt="palisade"
          className="w-[214px] h-[155px]"
        />
        <p className="font-normal body1 text-grey-black">38,960,000원 부터</p>
      </li>
      <li className="w-[214px] h-[155px] flex flex-col items-center gap-[8px]">
        <img src="src/assets/images/palisade.png" alt="palisade" />
        <p className="font-normal body1 text-grey-black">38,960,000원 부터</p>
      </li>
    </ul>
  );
}

export default CarsImageBox;
