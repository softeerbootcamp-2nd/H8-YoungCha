import type { Meta, StoryObj } from '@storybook/react';
import Select from './index';
import * as Icon from '@/assets/icons';

const meta: Meta<typeof Select> = {
  component: Select,
};

export default meta;

type Story = StoryObj<typeof Select>;

export const Default: Story = {
  render: () => (
    <>
      <Select type="active">시스템</Select>
      <br />
      <Select type="default">시스템</Select>
    </>
  ),
};

export const Size: Story = {
  render: () => (
    <>
      <Select type="IconActive" size="md">
        <span>이름 입력</span>
        <Icon.CheckActive />
      </Select>
      <br />
      <Select type="default" size="md">
        <span>이름 입력</span>
        <Icon.CheckInactive />
      </Select>
      <br />
      <br />
      <Select type="IconActive" size="lg">
        <span>연령이나 성별 입력</span>
        <Icon.CheckActive />
      </Select>
      <br />
      <Select type="default" size="lg">
        <span>연령이나 성별 입력</span>
        <Icon.CheckInactive />
      </Select>
    </>
  ),
};
