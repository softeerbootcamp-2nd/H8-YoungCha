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
  },
};

export const GuideMode: Story = {
  args: {
    name: 'Guide Mode',
    hashtag: '#나만을 위한 팰리세이드',
    minPrice: 38000000,
  },
};
