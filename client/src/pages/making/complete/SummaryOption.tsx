interface SummaryOptionProps {
  type?: string;
  name: string;
  price: string;
}
function SummaryOption({ type = '', name, price }: SummaryOptionProps) {
  return (
    <div className="flex items-center justify-between px-18px gap-65px py-16px">
      <span className="text-grey-004 title4 w-80px">{type}</span>
      <div className="flex items-center justify-between flex-1">
        <span className="text-grey-black title4">{name}</span>
        <span className="title4 text-grey-black">{`+${price}원`}</span>
      </div>
    </div>
  );
}

export default SummaryOption;
