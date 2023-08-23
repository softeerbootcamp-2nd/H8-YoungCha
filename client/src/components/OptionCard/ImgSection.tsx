import { OptionImageType } from '@/types/option';

interface ImgSectionProps {
  isActive: boolean;
  images: OptionImageType[];
  step: number;
}

function ImgSection({ images, step, isActive }: ImgSectionProps) {
  const imgUrl = images?.filter(({ imgType }) => imgType === 1)[0]?.imgUrl;
  const logoUrl = images?.filter(({ imgType }) => imgType === 2)[0]?.imgUrl;

  const imgElement = [
    ...Array(4).fill(null),
    <div
      className={`${
        isActive ? '' : 'opacity-40'
      } absolute w-108px top-20px right-20px`}
      key={`${step}-휠 선택`}
    >
      <img src={logoUrl} alt="H Genuine Accessories" />
    </div>,
    <img
      className="absolute bg-white rounded-full top-50px right-30px w-60px h-60px border-2px border-grey-002 border-box"
      src={imgUrl}
      alt="외장 색상"
      key={`${step}-외장 색상`}
    ></img>,
    <img
      src={imgUrl}
      className="absolute top-50px rounded-6px w-150px h-50px right-25px bg-grey-002 bg-animate-pulse"
      alt="내장 색상"
      key={`${step}-내장 색상`}
    />,
    <div
      className={`${
        isActive ? '' : 'opacity-40'
      } absolute top-20px right-20px w-96px`}
      key={`${step}-옵션 선택`}
    >
      {logoUrl && <img src={logoUrl} alt="H Genuine Accessories" />}
    </div>,
  ];

  return <div>{imgElement[step]}</div>;
}

export default ImgSection;
