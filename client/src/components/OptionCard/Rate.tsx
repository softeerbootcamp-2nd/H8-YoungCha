function Rate({
  rate,
  isSelfMode,
  isActive,
}: {
  rate: number;
  isSelfMode: boolean;
  isActive: boolean;
}) {
  const rateTextColor = isActive
    ? isSelfMode
      ? 'text-main-blue'
      : 'text-sub-blue'
    : 'text-grey-003';
  return (
    <div
      className={`${rateTextColor}
         body2 mt-10px mb-4px`}
    >
      {isSelfMode
        ? `구매자의 ${rate}%가 선택했어요!`
        : `나와 비슷한 ${rate}%가 선택한`}
    </div>
  );
}

export default Rate;
