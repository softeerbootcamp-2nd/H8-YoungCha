import ChevronRight from '@/assets/icons/ChevronRight';
import { Fragment, useState } from 'react';
import { Link } from 'react-router-dom';

const MAIN_OPTION = '핵심 옵션';

const GUIDE_MODE_DESCRIPTION = [
  {
    title: '내 관심 분야를 바탕으로 유형 분석.',
    detail: '선택해주신 태그를 바탕으로 나의 유형을 분석해드려요.',
  },
  {
    title: '개인맞춤 가이드와 함께 쉽게 옵션 선택!',
    detail: '나와 비슷한 유형의 사람들은 어떤 옵션을 선택했는지 알 수 있어요.',
  },
  {
    title: '나만의 멋진 차 완성!',
    detail: '나의 취향이 반영된 멋진 차가 완성돼요!',
  },
];

// TYPES
interface TrimCardProps {
  name: string;
  hashtag: string;
  minPrice: number;
  description: string;
  mainOptions: {
    imgUrl: string;
    description: string;
  }[];
  guide: boolean;
}
interface TrimHoverCardProps {
  name: string;
  description: string;
  mainOptions: {
    imgUrl: string;
    description: string;
  }[];
  guide: boolean;
}

function TrimHoverCard({
  name,
  description,
  mainOptions,
  guide = false,
}: TrimHoverCardProps) {
  return (
    <div className="absolute bottom-[calc(100%+16px)]">
      <div
        className="relative text-white rounded-md w-610px h-237px bg-gradient-to-r from-white/20 to-white/0 p-32px backdrop-blur-[6px]
      before:content-[''] before:absolute before:p-2px before:inset-0 before:rounded-6px before:border-trim-hover drop-shadow-[0_10px_16px_rgba(0,0,0,0.2)] flex items-center gap-24px"
      >
        <div className="flex flex-col justify-center flex-1 gap-y-6px">
          <div className="title4">{description}</div>
          <h2 className="font-medium font-hsans-head text-32px">{name}</h2>
        </div>
        <div className="mx-auto flex-2">
          {guide ? (
            <div className="flex flex-col gap-20px ">
              {GUIDE_MODE_DESCRIPTION.map(({ title, detail }, index) => {
                return (
                  <div key={index} className="flex gap-12px">
                    <div className="text-center rounded-full bg-main-blue w-20px h-20px body3">
                      {index + 1}
                    </div>
                    <div>
                      <div className="font-medium whitespace-pre-line title4">
                        {title}
                      </div>
                      <div className="whitespace-pre-line body3">{detail}</div>
                    </div>
                  </div>
                );
              })}
            </div>
          ) : (
            <>
              <div className="title5 pb-26px">{MAIN_OPTION}</div>
              <div className="flex items-center gap-30px">
                {mainOptions.map((option, index) => (
                  <Fragment key={option.description}>
                    {index !== 0 && (
                      <div className="bg-white w-1px h-40px"></div>
                    )}
                    <div className="flex flex-col items-center gap-10px">
                      <img
                        src={option.imgUrl}
                        alt={option.description}
                        className="w-60px h-40px"
                      ></img>
                      <div className="text-center whitespace-pre-line body3">
                        {option.description}
                      </div>
                    </div>
                  </Fragment>
                ))}
              </div>
            </>
          )}
        </div>
      </div>
    </div>
  );
}

function TrimCard({
  name = '',
  hashtag = '#',
  minPrice = 0,
  description,
  mainOptions,
  guide = false,
}: TrimCardProps) {
  const [isHovered, setIsHovered] = useState(false);

  return (
    <div className="relative">
      {isHovered && (
        <TrimHoverCard {...{ name, description, mainOptions, guide }} />
      )}
      <Link to={'/making/self/1'}>
        <div
          className="body3 bg-white rounded-6px p-20px w-192px h-123px text=grey-black hover:bg-main-blue hover:text-white transition-colors duration-200 cursor-pointer"
          onMouseEnter={() => setIsHovered(true)}
          onMouseLeave={() => setIsHovered(false)}
        >
          <div className="mb-8px ">{hashtag}</div>
          <div className="title2 mb-16px">{name}</div>
          <div className="flex items-center justify-between">
            <div className="opacity-80">
              {minPrice.toLocaleString()} 원 부터
            </div>
            <ChevronRight />
          </div>
        </div>
      </Link>
    </div>
  );
}

export default TrimCard;
