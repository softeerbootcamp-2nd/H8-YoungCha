import type { Meta, StoryObj } from '@storybook/react';
import TrimCard from './index';
import { withRouter } from 'storybook-addon-react-router-v6';

const meta: Meta<typeof TrimCard> = {
  component: TrimCard,
  argTypes: {},
  parameters: {
    layout: 'centered',
  },
  decorators: [withRouter],
};

export default meta;

type Story = StoryObj<typeof TrimCard>;

export const Default: Story = {
  args: {
    name: 'LeBlanc',
    hashtag: '#베스트셀러',
    minPrice: 10000,
    description: '모두가 선택한 베스트셀러',
    mainOptions: [
      {
        imgUrl: 'src/assets/mock/TrimCard/option1.svg',
        description: '20인치\n 알로이 휠',
      },
      {
        imgUrl: 'src/assets/mock/TrimCard/option2.svg',
        description: '서라운드 뷰\n 모니터',
      },
      {
        imgUrl: 'src/assets/mock/TrimCard/option3.svg',
        description: '클러스터\n (12.3인치 컬러 LCD)',
      },
    ],
  },
};

export const GuideMode: Story = {
  args: {
    name: 'Guide Mode',
    hashtag: '#나만을 위한 팰리세이드',
    minPrice: 38000000,
    description: '나에게 딱 맞는 구성으로',
    guide: true,
  },
};
