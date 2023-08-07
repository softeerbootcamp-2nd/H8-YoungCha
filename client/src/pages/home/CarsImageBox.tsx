type DataType = { imgUrl: string; minPrice: string };
interface CarsImageBoxProps {
  datas: DataType[];
}

function CarsImageBox({ datas }: CarsImageBoxProps) {
  return (
    <ul className="flex justify-between px-24px w-1024px">
      {datas.map(({ imgUrl, minPrice }) => (
        <li className="flex flex-col items-center gap-8px" key={minPrice}>
          <img src={imgUrl} alt="palisade" className="w-214px h-155px" />
          <p className="font-normal body1 text-grey-black">{minPrice}원 부터</p>
        </li>
      ))}
    </ul>
  );
}

export default CarsImageBox;
