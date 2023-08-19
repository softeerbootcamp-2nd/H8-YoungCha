import type { Meta, StoryObj } from '@storybook/react';
import EstimationSummary from './index';

const meta: Meta<typeof EstimationSummary> = {
  component: EstimationSummary,
};

export default meta;

type Story = StoryObj<typeof EstimationSummary>;

export const Default: Story = {
  args: {
    render: true,
  },
};
