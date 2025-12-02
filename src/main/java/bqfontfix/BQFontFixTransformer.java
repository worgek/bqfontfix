package bqfontfix;

import net.minecraftforge.fml.common.FMLLog;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;

public class BQFontFixTransformer implements IClassTransformer {

    private static final String TARGET_CLASS = "betterquesting.api2.utils.BqFontRenderer";

        @Override
            public byte[] transform(String name, String transformedName, byte[] basicClass) {

                    // TEMPORARY DEBUG LOGGING – REQUIRED TO FIND REAL CLASS NAME
                            FMLLog.log.info("[BQFontFix] Saw class: " + name + " / " + transformedName);

                                    if (name == null || basicClass == null) return basicClass;

                                            // match either name or transformedName
                                                    if (!name.equals(TARGET_CLASS)) {
                                                                return basicClass;
                                                                        }

                                                                                System.out.println("[BQFontFix] Patching class: " + name);

                                                                                        try {
                                                                                                    ClassReader cr = new ClassReader(basicClass);
                                                                                                                ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

                                                                                                                            ClassVisitor cv = new ClassVisitor(Opcodes.ASM5, cw) {
                                                                                                                                            @Override
                                                                                                                                                            public MethodVisitor visitMethod(int access, String methodName, String desc, String signature, String[] exceptions) {
                                                                                                                                                                                MethodVisitor mv = super.visitMethod(access, methodName, desc, signature, exceptions);
                                                                                                                                                                                                    return new RemoveGLTexParamCalls(Opcodes.ASM5, mv);
                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                };

                                                                                                                                                                                                                                            cr.accept(cv, ClassReader.SKIP_FRAMES);
                                                                                                                                                                                                                                                        return cw.toByteArray();

                                                                                                                                                                                                                                                                } catch (Exception e) {
                                                                                                                                                                                                                                                                            System.out.println("[BQFontFix] ERROR applying patch: " + e);
                                                                                                                                                                                                                                                                                        return basicClass;
                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                                    }

                                                                                                                                                                                                                                                                                                        private static class RemoveGLTexParamCalls extends MethodVisitor {

                                                                                                                                                                                                                                                                                                                public RemoveGLTexParamCalls(int api, MethodVisitor mv) {
                                                                                                                                                                                                                                                                                                                            super(api, mv);
                                                                                                                                                                                                                                                                                                                                    }

                                                                                                                                                                                                                                                                                                                                            @Override
                                                                                                                                                                                                                                                                                                                                                    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {

                                                                                                                                                                                                                                                                                                                                                                if (opcode == Opcodes.INVOKESTATIC &&
                                                                                                                                                                                                                                                                                                                                                                                owner.equals("org/lwjgl/opengl/GL11") &&
                                                                                                                                                                                                                                                                                                                                                                                                name.equals("glTexParameteri") &&
                                                                                                                                                                                                                                                                                                                                                                                                                desc.equals("(III)V")) {

                                                                                                                                                                                                                                                                                                                                                                                                                                System.out.println("[BQFontFix]   Removed GL11.glTexParameteri call");
                                                                                                                                                                                                                                                                                                                                                                                                                                                return; // skip
                                                                                                                                                                                                                                                                                                                                                                                                                                                            }

                                                                                                                                                                                                                                                                                                                                                                                                                                                                        super.visitMethodInsn(opcode, owner, name, desc, itf);
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    