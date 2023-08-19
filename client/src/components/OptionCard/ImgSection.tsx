import { useOptionCardContext } from '@/store/useOptionCardContext';
import { PowerTrainDetails } from '@/assets/mock/optionMock';

interface ImgSectionProps extends Pick<PowerTrainDetails, 'imgUrl'> {
  imgUrl: string;
  step: number;
}

function ImgSection({ imgUrl, step }: ImgSectionProps) {
  const { isActive } = useOptionCardContext();
  const imgElement = [
    ...Array(4).fill(null),
    <div
      className={`${isActive ? '' : 'opacity-40'} absolute top-20px left-265px`}
      key={`${step}-휠 선택`}
    >
      <img src={imgUrl} alt="H Genuine Accessories" />
    </div>,
    <div
      className="absolute bg-white rounded-full top-50px left-285px w-60px h-60px border-1px border-grey-002"
      key={`${step}-외장 색상`}
    ></div>,
    <img
      src={imgUrl}
      className="absolute top-50px rounded-6px w-150px h-50px left-200px"
      alt="내장 색상"
      key={`${step}-내장 색상`}
    />,
    <div
      className={`${isActive ? '' : 'opacity-40'} absolute top-20px left-265px`}
      key={`${step}-옵션 선택`}
    >
      <img src={imgUrl} alt="H Genuine Accessories" />
    </div>,
  ];

  return <div>{imgElement[step]}</div>;
}

export default ImgSection;
