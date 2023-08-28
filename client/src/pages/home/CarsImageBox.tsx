import { TrimType } from '@/types';
import { formatPrice } from '@/utils';

interface CarsImageBoxProps {
  trims: TrimType[];
}

function CarsImageBox({ trims }: CarsImageBoxProps) {
  return (
    <ul className="flex justify-between px-24px w-1024px">
      {trims.map(({ imgUrl, price }, index) => (
        <li
          className="flex flex-col items-center gap-8px"
          key={`car-image-${index}`}
        >
          <img src={imgUrl} alt="palisade" />
          <p className="font-medium  body1 text-grey-black">
            {formatPrice(price)} 부터
          </p>
        </li>
      ))}
    </ul>
  );
}

export default CarsImageBox;
