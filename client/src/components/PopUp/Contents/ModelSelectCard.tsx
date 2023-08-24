import { hydrogenElectricCars } from '@/assets/mock/ModelListMock';
import { formatPrice } from '@/utils';

interface ModelSelectCardProps {
  index: number;
  activeIndex: number;
  onClick: () => void;
}

function ModelSelectCard({
  index,
  activeIndex,
  onClick,
}: ModelSelectCardProps) {
  return (
    <div className="flex justify-center" role="none" onClick={onClick}>
      <div
        className={`flex gap-18px mt-12px px-23px py-30px w-307px h-100px rounded-6px border-1px border-[#EDF2FA] bg-[#EDF2FA] hover:border-grey-003 ease-in-out duration-300 ${
          activeIndex === index ? 'border-grey-003' : ''
        }`}
      >
        <img
          src={hydrogenElectricCars[index].imgSrc}
          alt="자동차 미리보기"
          className="w-84px h-40px"
        />
        <div>
          <p className="font-medium title4">
            {hydrogenElectricCars[index].name}
          </p>
          <p className="body2 text-grey-004">
            {formatPrice(hydrogenElectricCars[index].price)} 부터
          </p>
        </div>
      </div>
    </div>
  );
}

export default ModelSelectCard;
