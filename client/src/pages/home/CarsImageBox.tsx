interface DataType {
  imgUrl: string;
  minPrice: string;
}
interface CarsImageBoxProps {
  carsImageLists: DataType[];
}

function CarsImageBox({ carsImageLists }: CarsImageBoxProps) {
  return (
    <ul className="flex justify-between px-24px w-1024px">
      {carsImageLists.map(({ imgUrl, minPrice }) => (
        <li className="flex flex-col items-center gap-8px" key={minPrice}>
          <img src={imgUrl} alt="palisade" className="w-214px h-155px" />
          <p className="font-normal body1 text-grey-black">{minPrice}원 부터</p>
        </li>
      ))}
    </ul>
  );
}

export default CarsImageBox;
