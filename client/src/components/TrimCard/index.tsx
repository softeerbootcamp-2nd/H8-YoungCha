import ChevronRight from '@/assets/icons/ChevronRight';
import { Link } from 'react-router-dom';

// TYPES
interface TrimCardProps {
  name: string;
  hashtag: string;
  minPrice: number;
}

function TrimCard({ name = '', hashtag = '#', minPrice = 0 }: TrimCardProps) {
  return (
    <div className="relative">
      <Link to={'/making/self/1'}>
        <div className="body3 bg-white rounded-6px p-20px w-192px h-123px text=grey-black hover:bg-main-blue hover:text-white transition-colors duration-200 cursor-pointer">
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
