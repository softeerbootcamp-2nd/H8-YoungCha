import { OptionType } from '@/pages/making/type';

function getOptionGroupsTotalPrice(...objects: Record<string, OptionType>[]) {
  return objects.reduce(
    (sum, object) =>
      sum + Object.values(object).reduce((sum, { price }) => sum + price!, 0),
    0
  );
}

export default getOptionGroupsTotalPrice;
