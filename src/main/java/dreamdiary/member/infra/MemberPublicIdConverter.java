package dreamdiary.member.infra;

import dreamdiary.member.domain.model.MemberPublicId;

import javax.persistence.AttributeConverter;

class MemberPublicIdConverter implements AttributeConverter<MemberPublicId, String> {
    @Override
    public String convertToDatabaseColumn(final MemberPublicId attribute) {
        return null == attribute ? null : attribute.value();
    }

    @Override
    public MemberPublicId convertToEntityAttribute(final String dbData) {
        return new MemberPublicId(dbData);
    }
}
