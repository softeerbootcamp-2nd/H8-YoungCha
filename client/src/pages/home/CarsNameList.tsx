function CarsNameListBox() {
  return (
    <div className="h-[335px] bg-grey-001">
      <div className="max-w-7xl pt-[65px] m-auto">
        <h2 className="text-center font-hsans-head text-[40px] font-medium leading-[52px] tracking-[-1.6px] text-grey-black mb-[118px]">
          모델 한 눈에 비교하기
        </h2>
        <div className="flex justify-between px-[192px] font-hsans-head">
          <div className="flex flex-col gap-[16px] items-center">
            <h3 className="text-grey-black text-[28px] font-medium tracking-[-0.84px]">
              Exclusive
            </h3>
            <p className="body2">기본에 충실</p>
          </div>
          <div className="flex flex-col gap-[16px] items-center">
            <h3 className="text-grey-black text-[28px] font-medium tracking-[-0.84px]">
              Le Blanc (르블랑)
            </h3>
            <p className="body2">모두가 선택한 베스트셀러</p>
          </div>
          <div className="flex flex-col gap-[16px] items-center">
            <h3 className="text-grey-black text-[28px] font-medium tracking-[-0.84px]">
              Prestige
            </h3>
            <p className="body2">부담없는 고급감</p>
          </div>
          <div className="flex flex-col gap-[16px] items-center">
            <h3 className="text-grey-black text-[28px] font-medium tracking-[-0.84px]">
              Calligraphy
            </h3>
            <p className="body2">최고를 원한다면</p>
          </div>
        </div>
      </div>
    </div>
  );
}

export default CarsNameListBox;
