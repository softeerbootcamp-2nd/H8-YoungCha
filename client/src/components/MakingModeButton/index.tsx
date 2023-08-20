import { useState } from 'react';
import { Link } from 'react-router-dom';
import DetailCard from '@/components/MakingModeButton/DetailCard.tsx';
import ChevronRight from '@/assets/icons/ChevronRight';
import GuideModeDetailList from '@/components/MakingModeButton/GuideModeDetailList';
import MainOptionList from '@/components/MakingModeButton/MainOptionList';
import { getPriceTemplete } from '@/utils';
import { TrimType } from '@/types';

// TYPES
export interface MakingModeButtonProps
  extends Pick<TrimType, 'name' | 'hashTag' | 'price' | 'description'> {
  children?: React.ReactNode;
  to: string;
}

function MakingModeButton({
  children,
  name = '',
  hashTag = '#',
  price = 0,
  description,
  to = '/#',
}: MakingModeButtonProps) {
  const [isHovered, setIsHovered] = useState(false);

  return (
    <div className="relative">
      <div className="absolute bottom-[calc(100%+16px)]">
        {isHovered && (
          <DetailCard {...{ name, description }}>{children}</DetailCard>
        )}
      </div>
      <Link to={to}>
        <button
          className="body3 bg-white rounded-6px p-20px w-192px h-123px text=grey-black hover:bg-main-blue hover:text-white transition-colors duration-200 text-left"
          onMouseEnter={() => setIsHovered(true)}
          onMouseLeave={() => setIsHovered(false)}
        >
          <div className="mb-8px">{hashTag}</div>
          <div className="title2 mb-16px">{name}</div>
          <div className="flex items-center justify-between">
            <span className="opacity-80">{getPriceTemplete(price)} 부터</span>
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
