interface PriceSectionProps {
  isActive: boolean;
}

function PriceSection({ isActive }: PriceSectionProps) {
  function formatPrice(price: number) {
    return Intl.NumberFormat('ko-KR').format(price);
  }

  return (
    <div
      className={`${isActive ? 'text-main-blue' : 'text-grey-003'} body2`}
    >{`+ ${formatPrice(1480000)}원`}</div>
  );
}

export default PriceSection;
