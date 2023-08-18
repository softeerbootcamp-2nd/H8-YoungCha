interface PriceSectionProps {
  isActive: boolean;
  price: number;
}

function PriceSection({ isActive, price }: PriceSectionProps) {
  function formatPrice(price: number) {
    return Intl.NumberFormat('ko-KR').format(price);
  }

  return (
    <div
      className={`${isActive ? 'text-main-blue' : 'text-grey-003'} body2`}
    >{`+ ${formatPrice(price)}원`}</div>
  );
}

export default PriceSection;
