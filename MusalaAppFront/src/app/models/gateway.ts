import { Device } from "./device";

export class Gateway {
    id: number | undefined;
    name: string | undefined;
    ipv4: string | undefined;
    devices?: Device[];
}