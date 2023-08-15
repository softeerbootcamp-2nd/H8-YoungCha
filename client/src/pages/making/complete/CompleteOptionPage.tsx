import { useState } from 'react';
import Button from '@/components/Button';
import SummaryOptionsBox from './SummaryOptionsBox';
import DetailOption from './DetailOption';
import { CAR_COLOR, COMPLETE_OPTION_PAGE_TITLE } from '../constant';
import { ColorType } from '../type';

function CompleteOptionPage() {
  const [selectedColorType, setSelectedColorType] =
    useState<ColorType>('exterior');
  const activeColor = 'text-white bg-main-blue';
  const inactiveColor = 'text-main-blue bg-[#F2F2F2]';

  return (
    <div className="flex flex-col items-center w-full m-auto mt-60px gap-60px max-w-7xl px-128px pb-70px">
      <h1 className="whitespace-pre-line text-34px font-medium leading-[47.6px] tracking-[-1.36px] font-hsans-head text-grey-black text-center">
        {COMPLETE_OPTION_PAGE_TITLE}
      </h1>
      <img
        src="/src/assets/mock/images/palisade.png"
        alt="palisade"
        width={600}
        height={320}
      />
      <div className="flex">
        {CAR_COLOR.map(({ text, type }) => (
          <button
            className={`${
              selectedColorType === type ? activeColor : inactiveColor
            } py-8px px-32px title4`}
            key={type}
            onClick={() => setSelectedColorType(type)}
          >
            {text}
          </button>
        ))}
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
      </div>

      <div className="flex justify-between w-full">
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

      <div className="flex flex-col w-full gap-60px">
        <div className="flex items-center justify-between h-68px px-24px">
          <span className="title3 text-grey-black">견적 자세히 보기</span>
          <div className="flex items-center gap-14px">
            <span className="title4 text-grey-black">차량 총 견적 금액</span>
            <span className="font-hsans-head text-34px font-medium leading-[44.2px] tracking-[-1.02px] text-[#36383C]">
              47,270,000원
            </span>
          </div>
        </div>

        <div className="flex flex-col gap-60px">
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
  );
}

export default CompleteOptionPage;
