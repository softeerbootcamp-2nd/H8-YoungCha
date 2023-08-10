import { TrimType } from '@/assets/mock/mock';
import MakingModeButton from '@/components/MakingModeButton';

interface TrimCarsBoxProps {
  trims: TrimType[];
}
function TrimCarsBox({ trims }: TrimCarsBoxProps) {
  return (
    <div className="flex justify-between gap-16px">
      <MakingModeButton
        name="Guide Mode"
        hashtag="#나만을 위한 팰리세이드"
        minPrice={38000000}
        description="나에게 딱 맞는 구성으로"
        to="making/guide/1"
      >
        <MakingModeButton.GuideModeDetailList />
      </MakingModeButton>
      {trims.map(({ name, hashtag, minPrice, description }, index) => {
        return (
          <MakingModeButton
            name={name}
            hashtag={hashtag}
            minPrice={minPrice}
            description={description}
            to="making/self/1"
            key={`making-mode-button-${index}`}
          >
            <MakingModeButton.MainOptionList
              mainOptions={[
                {
                  imgUrl: '/src/assets/mock/TrimCard/option1.svg',
                  description: '20인치\n 알로이 휠',
                },
                {
                  imgUrl: '/src/assets/mock/TrimCard/option2.svg',
                  description: '서라운드 뷰\n 모니터',
                },
                {
                  imgUrl: '/src/assets/mock/TrimCard/option3.svg',
                  description: '클러스터\n (12.3인치 컬러 LCD)',
                },
              ]}
            />
          </MakingModeButton>
        );
      })}
    </div>
  );
}

export default TrimCarsBox;
