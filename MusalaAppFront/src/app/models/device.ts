import { Gateway } from "./gateway";

export class Device {
    id: number | undefined;
    vendor: string | undefined;
    creationDate: Date | undefined;
    status: string | undefined;
    gateway!: Gateway;
}