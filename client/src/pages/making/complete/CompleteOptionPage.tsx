import Button from '@/components/Button';
import SummaryOptionsBox from './SummaryOptionsBox';
import DetailOption from './DetailOption';

const TITLE = '나를 위한 팰리세이드가\n완성되었어요!';
function CompleteOptionPage() {
  return (
    <div className="flex flex-col items-center w-full m-auto mt-60px gap-60px max-w-7xl px-128px">
      <h1 className="whitespace-pre-line text-34px font-medium leading-[47.6px] tracking-[-1.36px] font-hsans-head text-grey-black text-center">
        {TITLE}
      </h1>
      <img
        src="/src/assets/mock/images/palisade.png"
        alt="palisade"
        width={600}
        height={320}
      />
      <div className="flex">
        <button className="text-white py-8px px-32px bg-main-blue title4">
          외부
        </button>
        <button className="text-main-blue py-8px px-32px title4 bg-[#F2F2F2]">
          내부
        </button>
      </div>
      <div className="w-full">
        <div className="flex items-center justify-between h-68px px-24px">
          <span className="title3 text-grey-black">견적 요약</span>
          <div className="flex items-center gap-14px">
            <span className="title4 text-grey-black">차량 총 견적 금액</span>
            <span className="font-hsans-head text-34px font-medium leading-[44.2px] tracking-[-1.02px] text-[#36383C]">
              47,270,000원
            </span>
          </div>
        </div>

        <SummaryOptionsBox
          title={'팰리세이드 Le Blanc (르블랑)'}
          price={'100,000,000'}
        >
          <SummaryOptionsBox.Option
            type="파워트레인"
            name="디젤 2.2"
            price="2,123,000"
          />
          <SummaryOptionsBox.Option name="디젤 2.2" price="2,123,000" />
        </SummaryOptionsBox>
        <SummaryOptionsBox title={'색상'} price={'100,000'}>
          <SummaryOptionsBox.Option name="디젤 2.2" price="2,123,000" />
        </SummaryOptionsBox>
        <SummaryOptionsBox title={'옵션'} price={'100,000'}>
          <SummaryOptionsBox.Option name="디젤 2.2" price="2,123,000" />
        </SummaryOptionsBox>
        <div className="flex justify-between">
          <Button size="xl" color="main-blue">
            구매 상담 신청
          </Button>
          <Button size="xl" color="white">
            시승 신청하기
          </Button>
          <Button size="xl" color="grey">
            공유하기
          </Button>
          <Button size="xl" color="grey">
            저장하기
          </Button>
        </div>

        <div className="flex flex-col gap-60px">
          <div className="flex items-center justify-between h-68px px-24px">
            <span className="title3 text-grey-black">견적 자세히 보기</span>
            <div className="flex items-center gap-14px">
              <span className="title4 text-grey-black">차량 총 견적 금액</span>
              <span className="font-hsans-head text-34px font-medium leading-[44.2px] tracking-[-1.02px] text-[#36383C]">
                47,270,000원
              </span>
            </div>
          </div>

          <div>
            <DetailOption type="trim" />
            <DetailOption type="trim" />
            <DetailOption type="trim" />
            <DetailOption type="trim" />
            <DetailOption type="trim" />
            <DetailOption type="trim" />
            <DetailOption type="basic-option" />
            <DetailOption type="selected-option" />
          </div>
        </div>
      </div>
    </div>
  );
}

export default CompleteOptionPage;
