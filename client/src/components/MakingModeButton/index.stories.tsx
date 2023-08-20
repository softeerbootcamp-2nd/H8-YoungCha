import type { Meta, StoryObj } from '@storybook/react';
import MakingModeButton, {
  MakingModeButtonProps,
} from '@/components/MakingModeButton';
import { withRouter } from 'storybook-addon-react-router-v6';

const meta: Meta<typeof MakingModeButton> = {
  component: MakingModeButton,

  parameters: {
    layout: 'centered',
  },
  decorators: [withRouter],
};

export default meta;

type Story = StoryObj<typeof MakingModeButton>;

export const Default: Story = {
  args: {
    name: 'LeBlanc',
    hashTag: '#베스트셀러',
    price: 10000,
    description: '모두가 선택한 베스트셀러',
    children: (
      <MakingModeButton.MainOptionList
        mainOptions={[
          {
            imgUrl: 'src/assets/mock/TrimCard/option1.svg',
            name: '20인치\n 알로이 휠',
          },
          {
            imgUrl: 'src/assets/mock/TrimCard/option2.svg',
            name: '서라운드 뷰\n 모니터',
          },
          {
            imgUrl: 'src/assets/mock/TrimCard/option3.svg',
            name: '클러스터\n (12.3인치 컬러 LCD)',
          },
        ]}
      />
    ),
  },
};

export const GuideMode: Story = {
  args: {
    name: 'Guide Mode',
    hashTag: '#나만을 위한 팰리세이드',
    price: 38000000,
    description: '나에게 딱 맞는 구성으로',
    children: <MakingModeButton.GuideModeDetailList />,
  },
};

export const Both: Story = {
  render: () => {
    return (
      <div className="flex gap-24px">
        <MakingModeButton {...(Default.args as MakingModeButtonProps)} />
        <MakingModeButton {...(GuideMode.args as MakingModeButtonProps)} />
      </div>
    );
  },
};
