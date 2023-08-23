import { formatPrice } from '@/utils';

interface PriceSectionProps {
  isActive: boolean;
  price: number;
}

function PriceSection({ isActive, price }: PriceSectionProps) {
  return (
    <div className={`${isActive ? 'text-main-blue' : 'text-grey-003'} body2`}>
      {formatPrice(price, true)}
    </div>
  );
}

export default PriceSection;
