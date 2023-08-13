import { TrimType } from '@/assets/mock/mock';

interface CarsImageBoxProps {
  trims: TrimType[];
}

function CarsImageBox({ trims }: CarsImageBoxProps) {
  return (
    <ul className="flex justify-between px-24px w-1024px">
      {trims.map(({ imgUrl, minPrice }, index) => (
        <li
          className="flex flex-col items-center gap-8px"
          key={`car-image-${index}`}
        >
          <img src={imgUrl} alt="palisade" className="w-214px h-155px" />
          <p className="font-normal body1 text-grey-black">{minPrice}원 부터</p>
        </li>
      ))}
    </ul>
  );
}

export default CarsImageBox;
