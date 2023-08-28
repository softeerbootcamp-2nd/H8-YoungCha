import { useState } from 'react';
import { Link } from 'react-router-dom';
import DetailCard from '@/components/MakingModeButton/DetailCard.tsx';
import ChevronRight from '@/assets/icons/popup/ChevronRight';
import GuideModeDetailList from '@/components/MakingModeButton/GuideModeDetailList';
import MainOptionList from '@/components/MakingModeButton/MainOptionList';
import { formatPrice } from '@/utils';
import { TrimType } from '@/types';

// TYPES
export interface MakingModeButtonProps
  extends Pick<TrimType, 'name' | 'hashTag' | 'price' | 'description'> {
  children?: React.ReactNode;
  to: string;
  position: 'first' | 'middle' | 'last';
  handleBackgroundImgUrlChange: () => void;
}

function MakingModeButton({
  children,
  name = '',
  hashTag = '#',
  price = 0,
  description,
  to = '',
  position,
  handleBackgroundImgUrlChange,
}: MakingModeButtonProps) {
  const [isHovered, setIsHovered] = useState(false);
  const absolutePosition =
    position === 'first'
      ? '-left-1px'
      : position === 'last'
      ? '-right-1px'
      : 'left-1/2 transform -translate-x-1/2';

  return (
    <div className="relative ">
      <div className={`absolute bottom-[calc(100%+16px)] ${absolutePosition}`}>
        {isHovered && (
          <DetailCard {...{ name, description }}>{children}</DetailCard>
        )}
      </div>
      <Link to={to}>
        <button
          className="body3 bg-white rounded-6px p-20px w-192px h-123px text=grey-black hover:bg-main-blue hover:text-white transition-colors duration-200 text-left "
          style={{ cursor: to === '' ? 'not-allowed' : 'pointer' }}
          onMouseEnter={() => {
            setIsHovered(true);
            handleBackgroundImgUrlChange();
          }}
          onMouseLeave={() => setIsHovered(false)}
        >
          <div className="mb-8px">{hashTag}</div>
          <div className="title2 mb-16px">{name}</div>
          <div className="flex items-center justify-between">
            <span className="opacity-80">{formatPrice(price)} 부터</span>
            <ChevronRight />
          </div>
        </button>
      </Link>
    </div>
  );
}

export default Object.assign(MakingModeButton, {
  MainOptionList,
  GuideModeDetailList,
});
