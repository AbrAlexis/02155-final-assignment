/**
 * RISC-V Instruction Set Simulator
 * <p>
 * A tiny first step to get the simulator started. Can execute just a single
 * RISC-V instruction.
 *
 * @author Martin Schoeberl (martin@jopdesign.com)
 */
public class IsaSim {

    static int pc;
    static int reg[] = new int[32];

    // Here the first program hard coded as an array
    static int progr[] = {
            // As minimal RISC-V assembler example
            0x00200093, // addi x1 x0 2
            0x00300113, // addi x2 x0 3
            0x002081b3, // add x3 x1 x2
    };

    public static void main(String[] args) {

        System.out.println("Hello RISC-V World!");

        pc = 0;

        for (; ; ) {

            int instr = progr[pc >> 2];
            int opcode = instr & 0x7f;
            int rd;
            int rs1;
            int imm;
            int funct3;
            int rs2;
            int funct7;

            switch (opcode) {
                // 

                case 0b0010011: // I-type arithmetic

                    rd = (instr >> 7) & 0x01f;
                    rs1 = (instr >> 15) & 0x01f;
                    imm = (instr >> 20);
                    funct3 = (instr >> 12) & 0x7;
                    
                    switch (funct3) {
                        case 0x0: // addi
                            reg[rd] = reg[rs1] + imm;
                            break;
                        default:
                            System.out.println("Funct3 " + funct3 + " not yet implemented");
                            break;
                    }
                    break;
                case 0b0000011: // I-type load
                    rd = (instr >> 7) & 0x01f;
                    rs1 = (instr >> 15) & 0x01f;
                    imm = (instr >> 20);
                    funct3 = (instr >> 12) & 0x7;
                    switch (funct3) {
                        case 0x0: // lb
                            System.out.println("Load byte not yet implemented");
                            break;
                        case 0x1: // lh
                            System.out.println("Load halfword not yet implemented");
                            break;
                        case 0x2: // lw
                            System.out.println("Load word not yet implemented");
                            break;
                        case 0x4: // lbu
                            System.out.println("Load byte unsigned not yet implemented");
                            break;
                        case 0x5: // lhu
                            System.out.println("Load halfword unsigned not yet implemented");
                            break;
                        default:
                            System.out.println("Funct3 " + funct3 + " not yet implemented");
                            break;
                    }
                    break;
                case 0b0100011: // S-type
                    rs1 = (instr >> 15) & 0x01f;
                    rs2 = (instr >> 20) & 0x01f;
                    imm = ((instr >> 7) & 0x01f) | ((instr >> 25) << 5);
                    funct3 = (instr >> 12) & 0x7;
                    switch (funct3) {
                        case 0x0: // sb
                            System.out.println("Store byte not yet implemented");
                            break;
                        case 0x1: // sh
                            System.out.println("Store halfword not yet implemented");
                            break;
                        case 0x2: // sw
                            System.out.println("Store word not yet implemented");
                            break;
                        default:
                            System.out.println("Funct3 " + funct3 + " not yet implemented");
                            break;
                    }
                    break;
                case 0b1100011: // B-type branch
                    rs1 = (instr >> 15) & 0x01f;
                    rs2 = (instr >> 20) & 0x01f;
                    imm = ((instr >> 31) << 12) | ((instr << 4) & 0x800) | ((instr >> 20) & 0x7E0) | ((instr >> 7) & 0x1E);
                    funct3 = (instr >> 12) & 0x7;
                    switch (funct3) {
                        case 0x0: // beq
                            System.out.println("Branch equal not yet implemented");
                            break;
                        case 0x1: // bne
                            System.out.println("Branch not equal not yet implemented");
                            break;
                        case 0x4: // blt
                            System.out.println("Branch less than not yet implemented");
                            break;
                        case 0x5: // bge
                            System.out.println("Branch greater or equal not yet implemented");
                            break;
                        case 0x6: // bltu
                            System.out.println("Branch less than unsigned not yet implemented");
                            break;
                        case 0x7: // bgeu
                            System.out.println("Branch greater or equal unsigned not yet implemented");
                            break;
                        default:
                            System.out.println("Funct3 " + funct3 + " not yet implemented");
                            break;
                    }
                    break;
                case 0b1101111: // J-type
                    rd = (instr >> 7) & 0x01f;
                    imm = ((instr >> 31) << 20) | (instr & 0x000FF000) | ((instr >> 9)  & 0x00000800) | ((instr >> 20) & 0x000007FE);
                    System.out.println("Jump and link not yet implemented");
                    break;

                case 0b1100111: // I-type jalr
                    rd = (instr >> 7) & 0x01f;
                    rs1 = (instr >> 15) & 0x01f;
                    imm = (instr >> 20);
                    funct3 = (instr >> 12) & 0x7;
                    System.out.println("Jump and link register not yet implemented");
                    break;

                case 0b0110111: // U-type lui
                    rd = (instr >> 7) & 0x01f;
                    imm = (instr >> 12);
                    reg[rd] = imm << 12;
                    break;
                case 0b0010111: // U-type auipc
                    rd = (instr >> 7) & 0x01f;
                    imm = (instr >> 12);
                    reg[rd] = (imm << 12) + pc;
                    break;

                case 0b0110011: // R-type

                 rd = (instr >> 7) & 0x01f;
                 rs1 = (instr >> 15) & 0x01f;
                 funct3 = (instr >> 12) & 0x7;
                 rs2 = (instr >> 20) & 0x01f;
                 funct7 = (instr >> 25);

                    switch (funct3) {
                        case 0x0:
                            switch (funct7) {
                                case 0x00:
                                    reg[rd] = reg[rs1] + reg[rs2]; // add instruction
                                    break;
                                case 0x20:
                                    reg[rd] = reg[rs1] - reg[rs2]; // sub instruction
                                    break;
                                default:
                                    System.out.println("Funct7 " + funct7 + " not yet implemented");
                                    break;
                            }
                            break;
                        case 0x1:
                            reg[rd] = reg[rs1] << reg[rs2]; // sll(shift left logical) instruction
                            break;
                        case 0x2: // slt(set less than) instruction
                            if (reg[rs1] < reg[rs2]) {
                                reg[rd] = 1;
                            } else {
                                reg[rd] = 0;
                            }
                            break;
                        case 0x3: // sltu(set less than unsigned) instruction
                            if (Integer.compareUnsigned(reg[rs1], reg[rs2]) < 0) {
                                reg[rd] = 1;
                            } else {
                                reg[rd] = 0;
                            }
                            break;
                        case 0x4: // xor(exclusive or) instruction
                            reg[rd] = reg[rs1] ^ reg[rs2];
                            break;
                        case 0x5: // srl(shift right logical) instruction
                            reg[rd] = reg[rs1] >>> reg[rs2];
                            break;
                        case 0x6: // sra(shift right arithmetic) instruction
                            reg[rd] = reg[rs1] >> reg[rs2];
                            break;
                        case 0x7: // or(inclusive or) instruction
                            reg[rd] = reg[rs1] | reg[rs2];
                            break;
                        case 0x8: // and(logical and) instruction
                            reg[rd] = reg[rs1] & reg[rs2];
                            break;

                        default:
                            System.out.println("Funct3 " + funct3 + " not yet implemented");
                            break;
                    }
 


                break;
                
                default:
                    System.out.println("Opcode " + opcode + " not yet implemented");
                    break;
            }
    

            pc += 4; // One instruction is four bytes
            if ((pc >> 2) >= progr.length) {
                break;
            }
            for (int i = 0; i < reg.length; ++i) {
                System.out.print(reg[i] + " ");
            }
            System.out.println();
        }

        System.out.println("Program exit");
        for (int i = 0; i < reg.length; ++i) {
            System.out.print(reg[i] + " ");
        }
        System.out.println();

    }

}
